const icons = {

    "Java":"☕",
    "DSA":"🧠",
    "DBMS":"💾",
    "Operating System":"💻",
    "Computer Networks":"🌐",
    "HR Interview":"💼"

};

loadCategories();

async function loadCategories(){

    try{

        const response =
            await fetch(
                "http://localhost:8080/api/questions/categories"
            );

        const categories =
            await response.json();

        const container =
            document.getElementById("categoryContainer");

        container.innerHTML = "";

        categories.forEach(category=>{

            const card =
            `
            <div class="category-card"
                 onclick="selectCategory('${category}')">

                <div class="category-icon">

                    ${icons[category] || "📘"}

                </div>

                <h3>${category}</h3>

            </div>
            `;

            container.innerHTML += card;
        });
    }
   catch(error){
        console.error("Failed to load categories:", error);
    }
}

function selectCategory(category){

    localStorage.setItem("selectedCategory", category);

    window.location.href = "difficulty.html";

}