(()=>{const e=document.getElementById("espressoButton"),t=document.getElementById("americanoButton"),n=document.getElementById("cappuccinoButton"),c=document.getElementById("latteButton"),o=document.getElementById("latteMacchiatoButton");e.addEventListener("click",(function(){order={name:"espresso",price:"2.50"},s(order)})),t.addEventListener("click",(function(){order={name:"americano",price:"3.00"},s(order)})),n.addEventListener("click",(function(){order={name:"cappuccino",price:"3.50"},s(order)})),c.addEventListener("click",(function(){order={name:"latte",price:"3.50"},s(order)})),o.addEventListener("click",(function(){order={name:"latte macchiato",price:"4.00"},s(order)}));const r=document.getElementById("bagelButton"),d=document.getElementById("croissantButton"),i=document.getElementById("sandwichButton"),a=document.getElementById("cheeseCakeButton");function s(e){fetch("http://localhost:8080/order-service",{method:"POST",headers:{"Content-type":"application/json"},body:JSON.stringify(e)}).then((e=>e.json())).then((e=>{alert(e)})).catch((e=>{console.error(e)}))}r.addEventListener("click",(function(){order={name:"bagel",price:"2.50"},s(order)})),d.addEventListener("click",(function(){order={name:"croissant",price:"2.00"},s(order)})),i.addEventListener("click",(function(){order={name:"sandwich",price:"6.50"},s(order)})),a.addEventListener("click",(function(){order={name:"cheese cake",price:"5.00"},s(order)}))})();