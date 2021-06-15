function fetchData(gameList=document.createElement('div')){
    fetch('/game/get')
        .then(reponse => reponse.json())
        .then(data => {
            var template = '';
            gameList.innerHTML = '';

            if(data.length > 0){
                data.forEach(item => {
                    template += `<a href="/game/${item.id}" class="list-group-item list-group-item-action">${item.name}</a>`;
                });
            }else{
                template += `
                    <hr>
                    <h2>No hay partidas disponibles</h2>
                `;
            }

            gameList.innerHTML = template;
        });
}

function main(){
    var refresh = document.getElementById('refresh-games');
    var gameList = document.getElementById('game-list');
    fetchData(gameList);

    refresh.addEventListener('click', function(){
        fetchData(gameList);
    });
}

window.addEventListener('load', main, true);