// Global Variables

let questions = [];

let currentQuestion = 0;

let selectedAnswers = [];

// Read Selection

const category = localStorage.getItem("selectedCategory");

const difficulty = localStorage.getItem("selectedDifficulty");

//  Show Selected Category

document.getElementById("categoryName").textContent = category;

document.getElementById("difficultyName").textContent = difficulty;

//  Load Questions From Spring Boot

loadQuestions();

async function loadQuestions(){

    try{

        const response =
            await fetch(
                `http://localhost:8080/api/questions/${encodeURIComponent(category)}/${encodeURIComponent(difficulty)}`
            );

        if(!response.ok){
            throw new Error("Unable to load questions.");
        }

        questions = await response.json();

        if(questions.length === 0){
            document.getElementById("question").textContent ="No questions available.";
            return;
        }

        renderQuestion();
    }

    catch(error){

        console.error(error);

        document.getElementById("question").textContent ="Unable to load questions.";

    }

}

// Render Question

function renderQuestion() {

    const q = questions[currentQuestion];

    // Question Number
    document.getElementById("questionNumber").innerHTML = `${currentQuestion + 1} / ${questions.length}`;

    // Progress Bar
    document.getElementById("progressBar").style.width = `${((currentQuestion + 1) / questions.length) * 100}%`;

    // Question
    document.getElementById("question").textContent = q.question;

    // Options
    const optionsContainer = document.getElementById("optionsContainer");

    optionsContainer.innerHTML = "";

    const options = q.options.split(",").map(option => option.trim());

    options.forEach(option => {

        const button = document.createElement("button");
        button.className = "option-btn";
        button.textContent = option;

        if (selectedAnswers[currentQuestion] === option) {

            button.classList.add("selected");

        }

        button.onclick = () => {

            selectedAnswers[currentQuestion] = option;
            renderQuestion();

        };

        optionsContainer.appendChild(button);

    });

    // Previous Button
    document.getElementById("prevBtn").disabled = currentQuestion === 0;

    // Next Button
    document.getElementById("nextBtn").disabled = currentQuestion === questions.length - 1;

}

// Previous Question

document
    .getElementById("prevBtn")
    .addEventListener("click", previousQuestion);

function previousQuestion(){

    if(currentQuestion > 0){
        currentQuestion--;
        renderQuestion();
    }

}

// Next Question

document
    .getElementById("nextBtn")
    .addEventListener("click", nextQuestion);

function nextQuestion(){

    if(currentQuestion < questions.length - 1){
        currentQuestion++;
        renderQuestion();
    }

}

// Submit Quiz

document
    .getElementById("submitBtn")
    .addEventListener("click", submitQuiz);

function submitQuiz(){

    let score = 0;

    questions.forEach((question,index)=>{
        if(selectedAnswers[index] === question.answer){
            score++;
        }
    });

    localStorage.setItem("quizScore", score);

    localStorage.setItem("totalQuestions", questions.length);

    localStorage.setItem("percentage", Math.round(score * 100 / questions.length));

    window.location.href = "result.html";

}