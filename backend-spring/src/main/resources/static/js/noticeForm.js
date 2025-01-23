document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("frm")
    const regBtn = document.getElementById("regBtn")
    const listBtn = document.getElementById("listBtn")
    const noticeType = document.getElementById("noticeType")
    const noticeTypeHidden = document.getElementById("noticeTypeHidden")

    function validateForm() {
        const title = document.getElementById("title")
        const content = document.getElementById("content")

        if (title.value.trim() === "") {
            alert("제목을 작성해 주세요.")
            title.focus()
            return false
        }
        if (content.value.trim() === "") {
            alert("공지사항 내용을 작성해주세요.")
            content.focus()
            return false
        }
        return true
    }

    function updateNoticeType() {
        noticeTypeHidden.value = noticeType.checked ? "긴급" : "일반"
    }

    noticeType.addEventListener("change", updateNoticeType)

    form.addEventListener("submit", function (e) {
        e.preventDefault()
        if (validateForm()) {
            this.submit()
        }
    })

    listBtn.addEventListener("click", () => {
        window.location.href = "/notice"
    })
})

