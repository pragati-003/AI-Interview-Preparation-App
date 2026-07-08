// Read Quiz Result

const score = Number(localStorage.getItem("quizScore")) || 0;

const total = Number(localStorage.getItem("totalQuestions")) || 0;

const percentage = Number(localStorage.getItem("percentage")) || 0;

const category = localStorage.getItem("selectedCategory") || "-";

const difficulty = localStorage.getItem("selectedDifficulty") || "-";

// Display Result

document.getElementById("percentage").textContent = percentage + "%";

document.getElementById("score").textContent = score + " / " + total;

document.getElementById("category").textContent = category;

document.getElementById("difficulty").textContent = difficulty;
 
// Result Message 

const heading = document.querySelector(".result-card h2");

if (percentage >= 90) {

    heading.textContent = "Excellent! 🎉";

}
else if (percentage >= 75) {

    heading.textContent = "Great Job! 👏";

}
else if (percentage >= 60) {

    heading.textContent = "Good Work 👍";

}
else if (percentage >= 40) {

    heading.textContent = "Keep Practicing 💪";

}
else {

    heading.textContent = "Don't Give Up 🚀";

}

// Buttons

document
    .getElementById("retryBtn")
    .addEventListener("click", () => {

        window.location.href = "categories.html";

    });

document
    .getElementById("dashboardBtn")
    .addEventListener("click", () => {

        window.location.href = "dashboard.html";

    });

// Update Dashboard Statistics

// Total Quizzes

let totalQuizzesTaken = Number(localStorage.getItem("totalQuizzesTaken")) || 0;

totalQuizzesTaken++;

localStorage.setItem("totalQuizzesTaken", totalQuizzesTaken);

// Highest Score

let highestScore = Number(localStorage.getItem("highestScore")) || 0;

if (percentage > highestScore) {

    highestScore = percentage;

    localStorage.setItem( "highestScore", highestScore);

}

// Average Score

let totalScore = Number(localStorage.getItem("totalScore")) || 0;

totalScore += percentage;

localStorage.setItem("totalScore", totalScore);

const averageScore = Math.round(totalScore / totalQuizzesTaken);

localStorage.setItem( "averageScore", averageScore);

// Difficulty Count

const diff = localStorage.getItem("selectedDifficulty");

if (diff === "Easy") {

    let easy = Number(localStorage.getItem("easyCount")) || 0;

    localStorage.setItem( "easyCount", easy + 1);

}

else if (diff === "Medium") {

    let medium = Number(localStorage.getItem("mediumCount")) || 0;

    localStorage.setItem("mediumCount",medium + 1);

}

else if (diff === "Hard") {

    let hard =Number(localStorage.getItem("hardCount")) || 0;

    localStorage.setItem("hardCount",hard + 1);

}    