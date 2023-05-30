    const espressoButton = document.getElementById("espressoButton");
    const americanoButton = document.getElementById("americanoButton");
    const cappuccinoButton = document.getElementById("cappuccinoButton");
    const latteButton = document.getElementById("latteButton");
    const latteMacchiatoButton = document.getElementById("latteMacchiatoButton");
    /*=============================================================================*/
    espressoButton.addEventListener("click", buyEspresso)
    americanoButton.addEventListener("click", buyAmericano)
    cappuccinoButton.addEventListener("click", buyCappuccino)
    latteButton.addEventListener("click", buyLatte)
    latteMacchiatoButton.addEventListener("click", buyLatteMacchiato)
    /*=============================================================================*/
    const bagelButton = document.getElementById("bagelButton");
    const croissantButton = document.getElementById("croissantButton");
    const sandwichButton = document.getElementById("sandwichButton");
    const cheeseCakeButton = document.getElementById("cheeseCakeButton");
    /*=============================================================================*/
    bagelButton.addEventListener("click", buyBagel);
    croissantButton.addEventListener("click", buyCroissant);
    sandwichButton.addEventListener("click", buySandwich);
    cheeseCakeButton.addEventListener("click", buyCheeseCake);

    function buyEspresso(){
        order = {
            name: 'espresso',
            price: '2.50'
        };
        sendOrder(order);
    }

    function buyAmericano() {
        order = {
          name: 'americano',
          price: '3.00',
        };
        sendOrder(order);
    }

    function buyCappuccino() {
        order = {
          name: 'cappuccino',
          price: '3.50',
        };
        sendOrder(order);
    }

    function buyLatte() {
        order = {
          name: 'latte',
          price: '3.50',
        };
        sendOrder(order);
    }

    function buyLatteMacchiato() {
        order = {
          name: 'latte macchiato',
          price: '4.00',
        };
        sendOrder(order);
    }

    function buyBagel(){
        order = {
            name: 'bagel',
            price: '2.50'
        };
        sendOrder(order);
    }

    function buyCroissant(){
        order = {
            name: 'croissant',
            price: "2.00"
        };
        sendOrder(order);
    }

    function buySandwich(){
        order = {
            name: 'sandwich',
            price: '6.50'
        };
        sendOrder(order);
    }

    function buyCheeseCake(){
        order = {
            name: 'cheese cake',
            price: '5.00'
        };
        sendOrder(order);
    }

    function sendOrder(order){
        fetch("http://localhost:8080/order-service",{
            method:"POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(order),
        })
        .then(response => response.json())
        .then(data => {
            alert(data)
        })
        .catch(error => {
            console.error(error);
        });
    }

