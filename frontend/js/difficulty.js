const category = localStorage.getItem("selectedCategory");

document.getElementById("selectedCategory").textContent = category;

const icons = {

    "Easy":"🟢",
    "Medium":"🟡",
    "Hard":"🔴"

};

loadDifficulties();

async function loadDifficulties(){

    try{

        const response =
            await fetch(
                `http://localhost:8080/api/questions/difficulties/${encodeURIComponent(category)}`
            );

        const difficulties =
            await response.json();

        const container =
            document.getElementById("difficultyContainer");

        container.innerHTML = "";

        difficulties.forEach(difficulty=>{

            container.innerHTML += `

            <div class="difficulty-card"

                 onclick="selectDifficulty('${difficulty}')">

                <div class="difficulty-icon">

                    ${icons[difficulty] || "📘"}

                </div>

                <h2>

                    ${difficulty}

                </h2>

                <p>

                    ${getDescription(difficulty)}

                </p>

            </div>

            `;

        });

    }
    catch(error){
        console.error("Failed to load difficulties:", error);
    }

}

function getDescription(level){

    if(level==="Easy")
        return "Basic Interview Questions";

    if(level==="Medium")
        return "Intermediate Interview Questions";

    if(level==="Hard")
        return "Advanced Interview Questions";

    return "Interview Questions";

}

function selectDifficulty(difficulty){

    localStorage.setItem("selectedDifficulty", difficulty);

    window.location.href = "questions.html";

}