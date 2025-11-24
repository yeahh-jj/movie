document.getElementById("signupBtn").addEventListener("click", registerUser);

function registerUser() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const nickName = document.getElementById("nickName").value.trim();

    if (!email || !password || !nickName) {
        alert("모든 항목을 입력해 주세요.");
        return;
    }

    fetch("/api/users/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password,
            nickName: nickName
        })
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw err; });
        }
        return response.text();
    })
    .then(message => {
        alert("회원가입이 완료되었습니다!");
        window.location.href = "/movie/login.html";   // 로그인 페이지로 이동
    })
    .catch(error => {
        console.error(error);
        alert(error.message || "회원가입 중 오류가 발생했습니다.");
    });

}

document.getElementById("backBtn").addEventListener("click", () => {
    history.back();
});