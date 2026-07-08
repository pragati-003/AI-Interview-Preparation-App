const total = Number(localStorage.getItem("totalQuizzesTaken")) || 0;

const average = Number(localStorage.getItem("averageScore")) || 0;

const highest =Number(localStorage.getItem("highestScore")) || 0;

document.getElementById("totalQuizzes").textContent = total;

document.getElementById("averageScore").textContent = average + "%";

document.getElementById("highestScore").textContent = highest + "%";

const easy = Number(localStorage.getItem("easyCount")) || 0;
const medium = Number(localStorage.getItem("mediumCount")) || 0;
const hard = Number(localStorage.getItem("hardCount")) || 0;

document.getElementById("easyCount").textContent = easy;
document.getElementById("mediumCount").textContent = medium;
document.getElementById("hardCount").textContent = hard;

const ctx = document
    .getElementById("difficultyChart")
    .getContext("2d");

new Chart(ctx,
    {
    type:"doughnut",

    data:{

        labels:[
            "Easy",
            "Medium",
            "Hard"
        ],

        datasets:[{

            data:[
                easy,
                medium,
                hard
            ],

            backgroundColor:[
                "#22c55e",
                "#f59e0b",
                "#ef4444"
            ],

            borderWidth:0

        }]

    },

    options:{

        responsive:true,

        plugins:{
            legend:{
                position:"bottom",
                labels:{

                    color:"white",
                    padding:20,
                    font:{
                        size:14
                    }

                }
            }
        }
    }
});
