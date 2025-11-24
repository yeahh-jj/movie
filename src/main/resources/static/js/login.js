document.getElementById("loginBtn").addEventListener("click", login);

function login() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if(!email || !password) {
        alert("이메일과 비밀번호를 모두 입력해 주세요.");
        return;
    }

    fetch("/api/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
    .then(res => {
        if(!res.ok) throw new Error("로그인 실패");
        return res.json();
    })
    .then(data => {
        console.log("로그인 성공:", data);
        window.location.href = "/movie";
    })
    .catch(err => {
        console.error(err);
        alert("이메일 또는 비밀번호가 올바르지 않습니다.");
    });
}

document.getElementById("registerUserBtn").addEventListener("click", () => {
    window.location.href = "/movie/signup.html";
});
