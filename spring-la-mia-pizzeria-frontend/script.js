//costanti
const apiUrl = "http://localhost:8080/api/v1/pizze"
const root = document.getElementById("root");
//funzione che prende gli ingredienti della pizze
const rendIngredienti = (ingredienti) => {
    console.log(ingredienti);
    let content
    if (ingredienti.length === 0) {
        content = "Niente ingredienti da mostrare"
    } else {
        content = '<ul class="list-unstyled">'
        ingredienti.forEach((ing) => {
            content += `<li >${ing.name}</li>`
        });
        content += '</ul>';
    }
    return content;

}

//funzione che renderizza la card della pizza
const rendPizza = (element) => {
    console.log(element);
    return `
    <div class="card h-100 m-4 shadow" style="width: 18rem;">
        <img src="${element.foto}" class="card-img-top" alt="...">
        <div class="card-body">
            <h4 class="card-title">${element.name}</h5>
            <h5 class="card-subtitle">${element.prezzo} €</h3>
            <p class="card-text">${element.descrizione}</p>
        </div>
        <div class="card-footer">${rendIngredienti(element.ingredienti)}</div>
    </div>`
}

//funzione che renderizza la gallery delle pizze
const renderPizzaList = (data) => {
    let content;
    console.log(data);
    //se ci sono dati da mostrare 
    if (data.length > 0) {
        //creo la gallery
        content = '<div class="row">'
        //itero sull'array di pizze
        data.forEach((element) => {
            content += '<div class="col">'
            //chiamo il metodo che restituisce la card di pizze
            content += rendPizza(element);
            content += '</div>';

        });
        content += '</div>';
    } else {
        //altrimenti messaggio che è vuota
        content = '<div class="alert alert-info"> La lista è vuota</div>'
    }
    //sostituisco il contenuto di root con il mio content
    root.innerHTML = content;
}

//funzione che chiama l'api e ottiene json di pizze
const getPizze = async () => {
    try {
        //ottengo i dati
        const response = await axios.get(apiUrl);
        //passo alla funzione che li renderizza
        renderPizzaList(response.data);
    } catch (error) {
        console.log(error);
    }
};

//codice che viene eseguito
getPizze();