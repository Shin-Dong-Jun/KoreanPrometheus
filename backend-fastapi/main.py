from fastapi import FastAPI
from dotenv import load_dotenv
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

# 허용할 출처(origin) 리스트
origins = [
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

# .env 파일 로드
load_dotenv()

# 환경 변수 가져오기
# api_key = os.getenv("UPSTAGE_API_KEY")

@app.post("/chat")
async def chat():
    next