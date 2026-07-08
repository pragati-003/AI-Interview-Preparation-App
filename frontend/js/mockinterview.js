// AI Mock Interview
// Part 1

let recognition;

let sessionId = "";

let currentQuestion = "";

let currentQuestionNumber = 1;

let startTime = 0;

let timerInterval;

let seconds = 0;

// Elements

const question = document.getElementById("question");

const answer = document.getElementById("answer");

const feedback = document.getElementById("feedback");

const startInterviewBtn = document.getElementById("startInterviewBtn");

const startBtn = document.getElementById("startBtn");

const stopBtn = document.getElementById("stopBtn");

const submitBtn = document.getElementById("submitBtn");

// Hide controls initially

startBtn.style.display = "none";

stopBtn.style.display = "none";

submitBtn.style.display = "none";

// Events

startInterviewBtn.onclick = startInterview;

startBtn.onclick = startRecording;

stopBtn.onclick = stopRecording;

submitBtn.onclick = submitAnswer;

// Timer

function startTimer(){

    seconds = 0;

    clearInterval(timerInterval);

    timerInterval = setInterval(()=>{

        seconds++;

        const min = String(Math.floor(seconds/60)).padStart(2,"0");

        const sec = String(seconds%60).padStart(2,"0");

        document.getElementById("timer").textContent = `${min}:${sec}`;

    },1000);

}

function stopTimer(){

    clearInterval(timerInterval);

}

// Start Interview

async function startInterview(){

    try{

        const response =
            await fetch(
                "http://localhost:8080/api/interview/start",
                {
                    method:"POST"
                }
            );

        const data = await response.json();

        sessionId = data.sessionId;

        currentQuestion = data.question;

        currentQuestionNumber = data.questionNumber;

        question.textContent = currentQuestion;

        document.getElementById("questionNumber").textContent = `${currentQuestionNumber} / 10`;

        updateProgressDots();

        updateInterviewTips(currentQuestion);

        startInterviewBtn.style.display = "none";

        startBtn.style.display = "inline-block";

        stopBtn.style.display = "inline-block";

        submitBtn.style.display = "inline-block";

        startBtn.disabled = false;

    }

    catch(error){

        console.error("Unable to start interview:", error);

    }

}

// Recording

function startRecording(){

    answer.value = "";

    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

    if(!SpeechRecognition){

        alert("Please use Google Chrome.");
        return;

    }

    recognition = new SpeechRecognition();

    recognition.lang = "en-US";

    recognition.continuous = true;

    recognition.interimResults = true;

    let finalTranscript = "";

    startTime = Date.now();

    startTimer();

    recognition.onresult = function(event){

        let interimTranscript = "";

        for(let i = event.resultIndex; i < event.results.length; i++){

            const text = event.results[i][0].transcript;
            if(event.results[i].isFinal){
                finalTranscript += text + " ";
            }else{
                interimTranscript += text;
            }
        }
        answer.value = finalTranscript + interimTranscript;
    };

    recognition.onerror = function(event){
            console.log(event.error);
        };

    recognition.onend = function(){

        stopTimer();

        stopBtn.innerHTML = "⏹ Stop Recording";

        stopBtn.disabled = true;

        startBtn.disabled = false;

        submitBtn.disabled = false;

    };

    recognition.start();

    startBtn.disabled = true;

    stopBtn.disabled = false;

}

// Stop Recording

function stopRecording(){

    if(!recognition) return;

    // Disable button immediately
    stopBtn.disabled = true;

    // Show user that processing has started
    stopBtn.innerHTML = "⏳ Processing...";

    recognition.stop();

}

// Submit Answer

async function submitAnswer(){

    try{

        const loading =  document.getElementById("loadingSection");

        loading.style.display = "block";

        loading.scrollIntoView({

            behavior:"smooth",
            block:"center"

        });

        // Reset

        document.getElementById("step1").innerHTML = "⏳ Grammar";

        document.getElementById("step2").innerHTML = "⏳ Technical Skills";

        document.getElementById("step3").innerHTML = "⏳ Confidence";

        document.getElementById("step4").innerHTML = "⏳ Communication";

        // Animate

        setTimeout(()=>{

            document.getElementById("step1").innerHTML = "✔ Grammar";

        },1000);

        setTimeout(()=>{

            document.getElementById("step2").innerHTML = "✔ Technical Skills";

        },2200);

        setTimeout(()=>{

            document.getElementById("step3").innerHTML = "✔ Confidence";

        },3400);

        setTimeout(()=>{

            document.getElementById("step4").innerHTML = "✔ Communication";

        },4600);

        

        const duration =(Date.now() - startTime) / 1000;

        const response = await fetch(
                "http://localhost:8080/api/interview/answer",
                {

                    method:"POST",

                    headers:{
                        "Content-Type":"application/json"
                    },

                    body:JSON.stringify({

                        sessionId:sessionId,
                        answer:answer.value,
                        duration:duration

                    })

                }
            );

        const data = await response.json();

        loading.style.display = "none";    

        // Live Metrics

        document.getElementById("confidence").innerHTML = data.confidenceScore + "%";

        document.getElementById("grammar").innerHTML = data.grammarScore + "%";

        document.getElementById("vocabulary").innerHTML = data.vocabularyScore + "%";

        document.getElementById("speechRate").innerHTML = data.speechRate.toFixed(1) + " WPM";

        document.getElementById("fluency").innerHTML = data.fluencyScore + "%";

        document.getElementById("fillerWords").innerHTML = data.fillerWords;

        // Feedback

        feedback.innerHTML =

        `

        <div class="feedback-card">

            <h2>

                Overall Score : ${data.overallScore}%

            </h2>

            <div class="score-grid">

                <div class="score-box">

                    <h4>Technical</h4>

                    <span>${data.technicalScore}%</span>

                </div>

                <div class="score-box">

                    <h4>Grammar</h4>

                    <span>${data.grammarScore}%</span>

                </div>

                <div class="score-box">

                    <h4>Communication</h4>

                    <span>${data.communicationScore}%</span>

                </div>

                <div class="score-box">

                    <h4>Vocabulary</h4>

                    <span>${data.vocabularyScore}%</span>

                </div>

                <div class="score-box">

                    <h4>Confidence</h4>

                    <span>${data.confidenceScore}%</span>

                </div>

                <div class="score-box">

                    <h4>Speech Rate</h4>

                    <span>${data.speechRate} WPM</span>

                </div>

            </div>

            <hr>

            <h3>💪 Strengths</h3>

            <ul>

                ${data.strengths
                    .map(x=>`<li>${x}</li>`)
                    .join("")}

            </ul>

            <h3>⚠ Weaknesses</h3>

            <ul>

                ${data.weaknesses
                    .map(x=>`<li>${x}</li>`)
                    .join("")}

            </ul>

            <h3>💡 Suggestions</h3>

            <ul>

                ${data.suggestions
                    .map(x=>`<li>${x}</li>`)
                    .join("")}

            </ul>

            <h3>✅ Ideal Answer</h3>

            <p>

                ${data.idealAnswer}

            </p>

        </div>

        `;

        // Interview Completed

        if(data.interviewCompleted){

            // Save only the final interview report

            sessionStorage.setItem("interviewReport", JSON.stringify(data.finalReport));

            localStorage.setItem( "interviewReport", JSON.stringify(data.finalReport));

            console.log(data.finalReport);

            window.location.href = "interviewreport.html";

            return;

        }

        // Next Question

        currentQuestion = data.question;

        currentQuestionNumber = data.questionNumber;

        question.innerHTML = currentQuestion;

        document
            .getElementById("questionNumber")
            .innerHTML =
            `${currentQuestionNumber} / 10`;

        answer.value = "";

        submitBtn.disabled = true;

        startBtn.disabled = false;

        updateProgressDots();

        updateInterviewTips(currentQuestion);

    }

    catch(error){

    loading.style.display = "none";
    console.log("Unable to evaluate answer.",error);

}

}

// Progress Dots

function updateProgressDots(){

    const dots = document.querySelectorAll(".dot");

    dots.forEach((dot,index)=>{

        dot.classList.remove("active");

        if(index < currentQuestionNumber){
            dot.classList.add("active");
        }
    });
}

// Dynamic Interview Tips

function updateInterviewTips(questionText){

    const tips = document.getElementById("tipsList");

    const question = questionText.toLowerCase();

    // Tell Me About Yourself

    if(question.includes("yourself")){

        tips.innerHTML = `

            <li>Introduce yourself in 60–90 seconds.</li>

            <li>Mention your education.</li>

            <li>Talk about your technical skills.</li>

            <li>Mention one or two projects.</li>

            <li>Finish with your career goals.</li>

        `;

    }

    // Projects

    else if(question.includes("project")){

        tips.innerHTML = `

            <li>Explain the problem statement.</li>

            <li>Mention technologies used.</li>

            <li>Describe your contribution.</li>

            <li>Explain challenges you faced.</li>

            <li>Mention the final outcome.</li>

        `;

    }

    // Strengths

    else if(question.includes("strength")){

        tips.innerHTML = `

            <li>Choose strengths relevant to the job.</li>

            <li>Support every strength with an example.</li>

            <li>Avoid generic answers.</li>

            <li>Be confident while speaking.</li>

            <li>Keep your answer concise.</li>

        `;

    }

    // Weaknesses

    else if(question.includes("weakness")){

        tips.innerHTML = `

            <li>Mention a genuine weakness.</li>

            <li>Explain how you're improving it.</li>

            <li>Never mention critical weaknesses.</li>

            <li>Stay positive.</li>

            <li>End with your progress.</li>

        `;

    }

    // Why should we hire you

    else if(question.includes("hire")){

        tips.innerHTML = `

            <li>Mention your skills.</li>

            <li>Talk about your projects.</li>

            <li>Show enthusiasm.</li>

            <li>Explain your value to the company.</li>

            <li>Be confident but humble.</li>

        `;

    }

    // OOP

    else if(question.includes("oop")){

        tips.innerHTML = `

            <li>Explain all four pillars.</li>

            <li>Give Java examples.</li>

            <li>Keep definitions simple.</li>

            <li>Mention real-world examples.</li>

            <li>Differentiate concepts clearly.</li>

        `;

    }

    // Collections

    else if(question.includes("collection")){

        tips.innerHTML = `

            <li>Explain List, Set and Map.</li>

            <li>Mention duplicates.</li>

            <li>Talk about ordering.</li>

            <li>Compare ArrayList and LinkedList.</li>

            <li>Mention time complexity if possible.</li>

        `;

    }

    // Spring Boot

    else if(question.includes("spring")){

        tips.innerHTML = `

            <li>Mention dependency injection.</li>

            <li>Explain auto configuration.</li>

            <li>Mention embedded Tomcat.</li>

            <li>Discuss REST APIs.</li>

            <li>Give a project example.</li>

        `;

    }

    // DBMS / SQL

    else if(question.includes("sql")
        || question.includes("database")
        || question.includes("dbms")){

        tips.innerHTML = `

            <li>Answer using examples.</li>

            <li>Mention normalization if relevant.</li>

            <li>Explain joins clearly.</li>

            <li>Discuss indexing when required.</li>

            <li>Keep definitions simple.</li>

        `;

    }

    // DSA

    else if(question.includes("array")
        || question.includes("stack")
        || question.includes("queue")
        || question.includes("tree")
        || question.includes("graph")){

        tips.innerHTML = `

            <li>Explain the approach first.</li>

            <li>Mention time complexity.</li>

            <li>Mention space complexity.</li>

            <li>Give a practical example.</li>

            <li>Avoid memorized definitions.</li>

        `;

    }

    // Default Tips

    else{

        tips.innerHTML = `

            <li>Think for a few seconds before speaking.</li>

            <li>Speak slowly and clearly.</li>

            <li>Maintain confidence.</li>

            <li>Support answers with examples.</li>

            <li>Avoid one-word answers.</li>

        `;

    }

}

// Initialize UI

startBtn.disabled = true;

stopBtn.disabled = true;

submitBtn.disabled = true;

document.getElementById("timer").innerHTML = "00:00";

updateProgressDots();