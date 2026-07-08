const report = JSON.parse(localStorage.getItem("interviewReport") );

if (!report) {

    alert("No interview report found.");
    window.location.href = "mockinterview.html";

}

// Overall Score

document.getElementById("overallScore").textContent = report.overallScore + "%";

document.getElementById("aiSummary").innerHTML = report.aiSummary;

// Skill Scores

function setProgress(barId, textId, value){
    document.getElementById(textId).textContent = `${value}%`;
    document.getElementById(barId).style.width = `${value}%`;
}

setProgress(
    "technicalBar",
    "technicalScore",
    report.technicalScore
);

setProgress(
    "communicationBar",
    "communicationScore",
    report.communicationScore
);

setProgress(
    "grammarBar",
    "grammarScore",
    report.grammarScore
);

setProgress(
    "vocabularyBar",
    "vocabularyScore",
    report.vocabularyScore
);



// Speech Analytics

// Confidence

setProgress(
    "confidenceSpeechBar",
    "confidenceSpeech",
    report.confidenceScore
);

// Fluency

setProgress(
    "fluencySpeechBar",
    "fluencySpeech",
    report.fluencyScore
);

// Speech Rate

document.getElementById("speechRateText").innerHTML = report.speechRate.toFixed(1) + " WPM";

document.getElementById("speechFeedback").innerHTML = report.speechFeedback;

// Convert Speech Rate into Progress Bar

let speechProgress = 100;

if(report.speechRate < 90){
    speechProgress = 50;
}
else if(report.speechRate < 110){
    speechProgress = 70;
}
else if(report.speechRate <= 160){
    speechProgress = 100;
}
else if(report.speechRate <= 180){
    speechProgress = 80;
}
else{
    speechProgress = 60;
}

document.getElementById("speechRateBar").style.width = speechProgress + "%";

// Filler Words

document.getElementById("fillerSpeech").innerHTML = report.averageFillerWords;

let fillerProgress = Math.max(
        0,
        100 - report.averageFillerWords * 10
    );

document.getElementById("fillerSpeechBar").style.width = fillerProgress + "%";    

// Strengths

const strong = document.getElementById("strongAreas");

strong.innerHTML = "";

if(report.strongAreas.length === 0){
    strong.innerHTML = "<li>No strong areas identified yet.</li>";
}
else{
    report.strongAreas.forEach(item=>{
        strong.innerHTML += `<li>${item}</li>`;
    });
}

// Improvement Areas

const improve = document.getElementById("improvementAreas");

improve.innerHTML = "";

if(report.improvementAreas.length === 0){
    improve.innerHTML = "<li>No improvement areas.</li>";
}
else{
    report.improvementAreas.forEach(item=>{
        improve.innerHTML += `<li>${item}</li>`;
    });
}

// Question Status

const questionStatus = document.getElementById("questionStatus");

questionStatus.innerHTML = "";

report.questionResults.forEach(result=>{

    let cssClass = "";

    let icon = "";

    if(result.status === "Excellent"){

        cssClass = "status-excellent";
        icon = "⭐";

    }

    else if(result.status === "Good"){

        cssClass = "status-good";
        icon = "✅";

    }

    else{

        cssClass = "status-improve";
        icon = "⚠";

    }

    questionStatus.innerHTML +=
    `
    <div class="question-status">

        <div class="question-number">

            Question ${result.questionNumber}

        </div>

        <div class="${cssClass}">

            ${icon} ${result.status}

        </div>

    </div>
    `;
});

// Overall Score Color

const circle = document.querySelector(".overall-circle");

if(report.overallScore >= 85){

    circle.style.background = "linear-gradient(135deg,#22c55e,#16a34a)";

}

else if(report.overallScore >= 70){

    circle.style.background = "linear-gradient(135deg,#2563eb,#38bdf8)";

}

else{

    circle.style.background = "linear-gradient(135deg,#f59e0b,#f97316)";

}