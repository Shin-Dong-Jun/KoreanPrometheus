package com.fastcampus.prometheus.domain.notice.dto;


import com.fastcampus.prometheus.domain.notice.dto.response.NoticeResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Setter
@Getter
public class PageHandler {
    private int totalCnt;
    private int pageSize;
    private int naviSize = 10;
    private int totalPage;
    private int page;
    private int beginPage;
    private int endPage;
    private boolean showPrev;
    private boolean showNext;

    public PageHandler(int totalCnt, int page, int pageSize) {
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;
        totalPage = (int)Math.ceil(totalCnt / pageSize); // 남는 페이지가 있을 수 있기 때문에 올림처리
        beginPage = this.page / naviSize * naviSize;
        endPage = Math.min(beginPage + naviSize -1,  totalPage); // endPage가 totalPage보다 작을 수 있기 때문에
        showPrev = beginPage != 0; // 시작 페이지가 0일 때만 안 나오면 된다.
        showNext = endPage != totalPage; // 다음으로 갈게 없으니까 보여주지 않음
    }

    @Override
    public String toString() {
        return "PageHandler [totalCnt=" + totalCnt + ", pageSize=" + pageSize + ", naviSize=" + naviSize
                + ", totalPage=" + totalPage + ", page=" + page + ", beginPage=" + beginPage + ", endPage=" + endPage
                + ", showPrev=" + showPrev + ", showNext=" + showNext + "]";
    }

}
