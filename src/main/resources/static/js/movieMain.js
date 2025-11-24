document.getElementById("menuBtn").addEventListener("click", () => {
    const offcanvas = new bootstrap.Offcanvas(document.getElementById("menuCanvas"));
    offcanvas.show();
});

function loadMovies() {
    fetch("/api/movie/list")
        .then(function (response) {
            if (!response.ok) {
                throw new Error("영화 목록을 불러올 수 없습니다.");
            }
            return response.json();
        })
        .then(function (movies) {
            renderMovieList(movies);
        })
        .catch(function (err) {
            console.error(err);
            alert("영화 목록 로딩 중 오류가 발생했습니다.");
        });
}

function renderMovieList(movies) {
    const template = document.getElementById("movieTemplate");
    const movieListDiv = document.getElementById("movieList");

    movieListDiv.innerHTML = "";

    movies.forEach(movie => {
        const clone = template.content.cloneNode(true);

        clone.querySelector(".movieItem").dataset.id = movie.id;
        clone.querySelector(".movieTitle").textContent = movie.title;
        clone.querySelector(".movieRating").textContent = `⭐️ ${movie.rating ?? "?"}`;
        clone.querySelector(".movieSummary").textContent = movie.description ?? "해당 영화에 대한 정보가 없습니다.";

        movieListDiv.appendChild(clone);
    });
}

document.getElementById("movieList").addEventListener("click", function(e) {
    const item = e.target.closest(".movieItem");
    if (!item) return;

    const movieId = item.dataset.id;
    window.location.href = `/movie/movieDetail.html?id=${movieId}`;
});

window.addEventListener("DOMContentLoaded", loadMovies);