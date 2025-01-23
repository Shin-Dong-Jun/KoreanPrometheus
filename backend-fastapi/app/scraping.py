# 스크래핑을 위한 라이브러리 import
import requests
from bs4 import BeautifulSoup # 모듈 확인 필요
import json

# Target URL (스크래핑 대상 URL) -> 일단 옷 샘플 하나만 해주세요( feat.영제님)
URL = "https://spao.com/product/qr-%EC%97%AC%EC%84%B1-%EC%98%A4%EB%B2%84%ED%95%8F-%ED%94%8C%EB%9E%80%EB%84%AC-%EC%85%94%EC%B8%A0spywf11w91/17684/category/64/display/1/"

# iframe URL (소재 정보가 포함된 URL)
IFRAME_URL = "https://fit9.cre.ma/spao.cafe24.com/fit/products/17684/combined_detail?iframe=1&iframe_id=crema-fit-product-combined-detail-4&app=0&parent_url=https%3A%2F%2Fspao.com%2Fproduct%2Fqr-%25EC%2597%25AC%25EC%2584%25B1-%25EC%2598%25A4%25EB%25B2%2584%25ED%2595%258F-%25ED%2594%258C%25EB%259E%2580%25EB%2584%25AC-%25EC%2585%2594%25EC%25B8%25A0spywf11w91%2F17684%2Fcategory%2F64%2Fdisplay%2F1%2F&nonmember_token=&secure_device_token=V267e19b5671301c99d270978b1c62f03909893e82344fd4d756440de3c6b009ce43750fb9bc5151930ae9a1637541c10e"

# HTTP 요청 헤더값 설정 (User-Agent)
# 왜 써요? (feat.영제님 : 봇이 보내는 요청 -> 차단 될 가능성 있음 / 사람이 보내는 것처럼 보이게 하면? 차단 X )
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
}

# GET 요청을 보내 웹 페이지 가져오기 / requests의 기능 한번 더 살펴보기(생각보다 다양함)
response = requests.get(URL, headers=headers)

# 요청이 성공했는지
if response.status_code == 200:
    print("페이지를 성공적으로 가져왔습니다!")
    
    # BeautifulSoup으로 HTML 파싱
    soup = BeautifulSoup(response.text, 'html.parser')

    # 데이터 추출하기
    # CSS Selector 사용
    
    # 카테고리 정보 (상의, 하의 등)
    category_element = soup.select_one('#contents > div.xans-element-.xans-product.xans-product-headcategory.path > ol > li:nth-child(2) > a')
    category = category_element.text.strip() if category_element else "카테고리 없음"
    
    # 상품명
    product_name_element = soup.select_one('#contents > div.xans-element-.xans-product.xans-product-detail > div.cboth.detailArea > div.infoArea > div > div > div.prd_info > div.headingArea > div.cboth.detail_name')
    product_name = product_name_element.text.strip() if product_name_element else "상품명 없음"
    
    # 가격
    price_element = soup.select_one('#span_product_price_text')
    price = int(price_element.text.strip().replace(',', '')) if price_element else 0  # 가격은 숫자로 변환(, 빼야됨)
    
    # 이미지 URL
    image_element = soup.select_one('#contents > div.xans-element-.xans-product.xans-product-detail > div.cboth.detailArea > div.xans-element-.xans-product.xans-product-addimage.listImga > ul > li > img')
    image = image_element['src'] if image_element else "이미지 없음"
    
    # 색상 정보 (값이 여러개인거 처리되게 해야함)
    #contents > div.xans-element-.xans-product.xans-product-detail > div.cboth.detailArea > div.infoArea > div > div > div.prd_option > table > tbody:nth-child(3) > tr > td > ul
    color_elements = soup.select('#contents > div.xans-element-.xans-product.xans-product-detail > div.cboth.detailArea > div.infoArea > div > div > div.prd_option > table > tbody:nth-child(3) > tr > td > ul > li')
    color = ', '.join([color.text.strip() for color in color_elements]) if color_elements else "색상 없음"

    # 사이즈 정보
    #contents > div.xans-element-.xans-product.xans-product-detail > div.cboth.detailArea > div.infoArea > div > div > div.prd_option > table > tbody:nth-child(4) > tr > td > ul
    size_elements = soup.select('#contents > div.xans-element-.xans-product.xans-product-detail > div.cboth.detailArea > div.infoArea > div > div > div.prd_option > table > tbody:nth-child(4) > tr > td > ul > li')
    size = ', '.join([size.text.strip() for size in size_elements]) if size_elements else "사이즈 없음"

    # 소재 정보 (iframe을 통해 가져오기)
    # iframe 페이지에서 소재 정보 추출
    # iframe 페이지에서 소재 정보 추출
    iframe_response = requests.get(IFRAME_URL, headers=headers)
    if iframe_response.status_code == 200:
        iframe_soup = BeautifulSoup(iframe_response.text, 'html.parser')

        # "소재: "라는 키워드를 포함한 텍스트를 찾기
        # fit-product-combined-detail > div.fit_product_combined_detail__body > div.fit_product_combined_detail_size_modules.fit_product_combined_detail_size_modules--border > div.fit_product_combined_detail__left_content > div > div > div.fit_product_combined_fit_product > div.fit_product_combined_fit_product__info > div > div:nth-child(3)
        material_element = iframe_soup.find(string=lambda x: x and "소재" in x)

        # 소재 정보가 포함된 텍스트를 추출
        material = material_element.strip() if material_element else "소재 정보 없음"


    # JSON 데이터 생성
    product_data = {

        "id": 1,
        "type": "top",  # 상품 유형나오게 (상의, 하의 등등)
        "gender": "female", # 남성옷일 경우에는 값을 바꿔줘야되나...?
        "title": product_name,
        "price": price,
        "color": color,
        "size": size,
        "material": material, # 소재정보
        "image": image,
        "url": URL
    }
    
    # JSON 파일로 저장
    with open('spao_product.json', 'w', encoding='utf-8') as json_file:
        json.dump([product_data], json_file, ensure_ascii=False, indent=4)

    print("JSON 파일로 저장되었습니다: spao_product.json")

else:
    print(f"페이지를 가져오는 데 실패했습니다. 상태 코드: {response.status_code}")
