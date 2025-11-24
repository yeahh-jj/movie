const urlParams = new URLSearchParams(window.location.search);
const movieId = urlParams.get("id");

let screeningMap = {};

document.getElementById("backBtn").addEventListener("click", () => {
    history.back();
});

document.getElementById("menuBtn").addEventListener("click", () => {
    const canvas = document.getElementById("menuCanvas");
    const offcanvas = new bootstrap.Offcanvas(canvas);
    offcanvas.show();
});

function loadMovieDetail() {
    fetch(`/api/movie/${movieId}`)
        .then(res => {
            if (!res.ok) throw new Error("영화 정보를 불러올 수 없습니다.");
            return res.json();
        })
        .then(movie => {
            document.getElementById("title").textContent = movie.title;
            document.getElementById("genre").textContent = movie.genre;
            document.getElementById("runningTime").textContent = movie.runningTime + "분";
            document.getElementById("director").textContent = movie.director;
            document.getElementById("description").textContent = movie.description;
            document.getElementById("averageRating").textContent = movie.averageRating || "0.0";

            loadReviews();
        })
        .catch(err => {
            console.error(err);
            alert("영화 정보를 가져오는 중 오류가 발생했습니다.");
        });
}

function loadReviews() {
    fetch(`/api/review/movie/${movieId}`)
        .then(res => {
            if (!res.ok) throw new Error("리뷰 정보를 불러올 수 없습니다.");
            return res.json();
        })
        .then(reviews => {
            const list = document.getElementById("reviewList");
            list.innerHTML = "";

            reviews.forEach(r => {
                list.innerHTML += `
                    <div class="border p-2 rounded mb-2">
                        <div><strong>${r.nickname}</strong> ⭐ ${r.rating}</div>
                        <div>${r.comment}</div>
                    </div>
                `;
            });
        })
        .catch(err => {
            console.error(err);
            alert("리뷰 로딩 실패");
        });
}

document.getElementById("resesrvationBtn").addEventListener("click", () => {
    loadScreenings();

    const canvas = document.getElementById("reservationCanvas");
    const offcanvas = new bootstrap.Offcanvas(canvas);
    offcanvas.show();
});

function loadScreenings() {
    fetch(`/api/screenings/movie/${movieId}`)
        .then(res => {
            if (!res.ok) throw new Error("상영 정보를 불러올 수 없습니다.");
            return res.json();
        })
        .then(screenings => {

            const select = document.getElementById("screenTimeSelect");
            select.innerHTML = `<option value="">상영 시간을 선택하세요</option>`;
            screeningMap = {};

            screenings.forEach(s => {
                const available = s.totalSeats - s.reservedSeats;
                screeningMap[s.id] = available;

                const option = document.createElement("option");
                option.value = s.id;
                option.textContent = `${formatTime(s.startTime)} (잔여 ${available}석)`;
                select.appendChild(option);
            });
        })
        .catch(err => {
            console.error(err);
            alert("상영 정보 로딩 실패");
        });
}

document.getElementById("screenTimeSelect").addEventListener("change", () => {
    const screeningId = document.getElementById("screenTimeSelect").value;
    const seatCountInput = document.getElementById("seatCount");

    if (!screeningId) {
        seatCountInput.max = 1;
        return;
    }

    const available = screeningMap[screeningId];

    seatCountInput.max = available;
    seatCountInput.value = 1;

    if (available === 0) {
        alert("해당 상영은 매진되었습니다.");
    }
});

document.getElementById("submitReservationBtn").addEventListener("click", () => {
    const screeningId = document.getElementById("screenTimeSelect").value;
    const numSeats = document.getElementById("seatCount").value;

    if (!screeningId) {
        alert("상영 시간을 선택하세요.");
        return;
    }

    fetch("/api/reservation/reserve", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            screeningId: Number(screeningId),
            numSeats: Number(numSeats)
        })
    })
        .then(res => {
            if (!res.ok) throw new Error("예매 실패");
            return res.json();
        })
        .then(data => {
            alert("예매가 완료되었습니다!");
        })
        .catch(err => {
            console.error(err);
            alert("예매 중 오류가 발생했습니다.");
        });
});

function formatTime(dateTime) {
    return dateTime.substring(11, 16);
}

loadMovieDetail();