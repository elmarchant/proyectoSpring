function main(){
    var attackArea = document.querySelector('table.attack-area');
    var attackCells = attackArea.querySelectorAll('td');
    var xVal = document.getElementById('xVal');
    var yVal = document.getElementById('yVal');
    var attack = document.getElementById('attack');

    attackCells.forEach(item => {
        item.addEventListener('click', function(){
            var x = this.dataset.x;
            var y = this.dataset.y;

            if(!this.innerHTML.includes('·')){
                xVal.value = x;
                yVal.value = y;
                attack.submit();
            }
        });
    });
}

window.addEventListener('load', main, true);