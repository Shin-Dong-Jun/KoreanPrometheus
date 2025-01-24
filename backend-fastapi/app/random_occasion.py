
# "occasion":과 "style": 를 카테고리화
# 관련해서 각각의 값이 배열에 담길 수 있게 하고 해당 값들을 랜덤으로 가지고 올 수 있게 작업
# json형식으로 저장된 스크래핑 데이터에 occasion 값과 style 값을 랜덤으로 2개씩 부여
# 랜덤으로 값이 부여된 후에 다시 json형식의 파일로 재저장

import random
import json

# 딕셔너리(dictionary) 또는 튜플(tuple)을 사용할 수 있다 -> 각 값에 해당하는 설명을 연결하여 저장할 수 있다.
# occasion 딕셔너리 (값과 설명을 포함)
occasion_dict = {
    "wedding": "결혼식",
    "office": "회사",
    "party": "파티",
    "casual": "일상",
    "formal": "공식 행사",
    "date": "데이트",
    "travel": "여행",
    "sport": "운동",
    "beach": "바캉스",
    "homewear": "홈웨어"
}

# JSON 파일 불러오기
input_file = 'spao_data.json'  # 입력 파일(수정이 필요한 파일) 이름 -> 필요할 경우 수정해서 사용
output_file = 'spao_data_updated.json'  # 수정 후 저장할 파일 이름 ->  occasion 과 style을 추가 한 뒤 저장될 파일 이름(필요시 수정)

# JSON 데이터 읽기
with open(input_file, 'r', encoding='utf-8') as f:
    data = json.load(f)

# 각 항목에 랜덤으로 occasion추가
for item in data:
    # 랜덤 occasion선택
    random_occasion = random.choice(list(occasion_dict.keys()))
    
    # 추가하기
    item['occasion'] = random_occasion

# 수정된 데이터 저장
with open(output_file, 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False, indent=4)

print(f"데이터가 성공적으로 업데이트되고 {output_file}에 저장되었습니다!")



