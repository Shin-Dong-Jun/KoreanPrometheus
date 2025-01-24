from fastapi import FastAPI, HTTPException
from dotenv import load_dotenv
from fastapi.middleware.cors import CORSMiddleware
from langchain_core.output_parsers import StrOutputParser
from pydantic import BaseModel
import time

app = FastAPI()

# 허용할 출처(origin) 리스트
origins = [
    "http://localhost",  # 프론트엔드 개발 환경
    "http://localhost:8080",  # 프론트엔드 개발 환경
]

# CORS 미들웨어 추가
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,  # 허용할 출처
    allow_credentials=True, # 쿠키 허용 여부
    allow_methods=["*"],    # 허용할 HTTP 메서드
    allow_headers=["*"],    # 허용할 HTTP 헤더
)

import json, os
file_path = "../resources/spao_woman2.json"

if os.path.exists(file_path):
    with open(file_path, "r", encoding="utf-8") as file:
        content = file.read().strip()

    if not content:
        print("⚠ 파일이 비어 있습니다.")
        json_data = []
    else:
        try:
            json_data = json.loads(content)
            print("✅ JSON 파일 로드 성공!")
        except json.JSONDecodeError:
            print("⚠ JSON 형식 오류!")
            json_data = []
else:
    print(f"⚠ 파일을 찾을 수 없습니다: {file_path}")
    json_data = []

# .env 파일 로드
load_dotenv()

# 환경 변수 가져오기
api_key = os.getenv("UPSTAGE_API_KEY")

from langchain_upstage.chat_models import ChatUpstage
model_name = "solar-pro"
llm = ChatUpstage(
    model_name="solar-pro",
    upstage_api_key=api_key,
    upstage_api_base="https://api.upstage.ai/v1/solar",
    temperature=0
)

from langchain.prompts import PromptTemplate
context = json_data

prompt_template_top = PromptTemplate.from_template("""
당신은 의류 추천 시스템입니다.  
반드시 제공된 JSON 데이터에서만 추천 제품을 선택해야 합니다.  
절대로 새로운 데이터를 생성하지 마세요.
---
질문: {question}
`occasion`의 리스트에 속하는 유사한 제품만 반환(e.g. 질문: 회사 출근룩 추천해 줘, "occasion": "회사")
모든 데이터(id,type,gender,title,price,color,size,material,image,url)를 recommendation에 추가하고, 반환하는 JSON 안에 각각 이유(reason)도 추가한 뒤, JSON 형식으로 반환
---
JSON 데이터: {context}
⚠️⚠️⚠️ **중요 주의사항** ⚠️⚠️⚠️
1. `{gender}` 복장만 추천하세요.
2. **반드시 위의 JSON 데이터에서 1개만 추천할 것** (절대 새로운 데이터를 생성하지 마세요).
3. `top`인 상품 1개만 추천하세요.
""")

prompt_template_bottom = PromptTemplate.from_template("""
당신은 의류 추천 시스템입니다.  
반드시 제공된 JSON 데이터에서만 추천 제품을 선택해야 합니다.  
절대로 새로운 데이터를 생성하지 마세요.
---
질문: {question}
`occasion`의 리스트에 속하는 유사한 제품만 반환(e.g. 질문: 회사 출근룩 추천해 줘, "occasion": "회사")
모든 데이터(id,type,gender,title,price,color,size,material,image,url)를 recommendation에 추가하고, 반환하는 JSON 안에 각각 이유(reason)도 추가한 뒤, JSON 형식으로 반환
---
JSON 데이터: {context}
⚠️⚠️⚠️ **중요 주의사항** ⚠️⚠️⚠️
1. `{gender}` 복장만 추천하세요.
2. **반드시 위의 JSON 데이터에서 1개만 추천할 것** (절대 새로운 데이터를 생성하지 마세요).
3. `bottom`인 상품 1개만 추천하세요.
""")

prompt_template = PromptTemplate.from_template("""
당신은 의류 추천 시스템입니다.  
반드시 제공된 JSON 데이터에서만 추천 제품을 선택해야 합니다.  
절대로 새로운 데이터를 생성하지 마세요.
---
질문: {question}
`occasion`과 유사한 제품만 반환(e.g. 질문: 회사 출근룩 추천해 줘, "occasion": "회사")
JSON 데이터1와 JSON 데이터2를 모든 데이터(id,type,gender,title,price,color,size,material,image,url)를 담아 2개의 recommendation로 만들어 줘
---
JSON 데이터1: {context1}
JSON 데이터2: {context2}
""")

class QueryParams(BaseModel):
    promptInput: str
    gender: str

@app.post("/chat")
async def chat(data: QueryParams):
    print(f"data : {data}")
    gender = "여성" if data.gender=="female" else "남성"
    
    chain_top = prompt_template_top | llm | StrOutputParser()
    chain_bottom = prompt_template_bottom | llm | StrOutputParser()
    chain = prompt_template | llm | StrOutputParser()
        
    try:
        start_time = time.time()
        
        # 상의 추천
        response_top = await chain_top.ainvoke({
            "question": data.promptInput,
            "context": json_data,
            "gender": gender
        })
        # 하의 추천
        response_bottom = await chain_bottom.ainvoke({
            "question": data.promptInput,
            "context": json_data,
            "gender": gender
        })
        # 상하의 세트
        response = await chain.ainvoke({
            "question": "JSON 데이터들을 합쳐줘",
            "context1": response_top,
            "context2": response_bottom
        })
        end_time = time.time()

        print(f"🚀 LLM 응답 속도: {end_time - start_time:.2f}초")
        print(f"response : {response}")
        return {"response": response}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
