import { Component } from '@angular/core';

@Component({
  selector: 'app-finish-market-button',
  imports: [],
  templateUrl: './finish-market-button.html',
  styleUrl: './finish-market-button.scss',
})
export class FinishMarketButton {
  isLoading = false;

  finishMarket(): void {
    this.isLoading = true;

    fetch('http://localhost:8080/api/finish-market', {
      method: 'POST'
    })
      .then(response => {
        if (response.ok) {
          console.log('Market finished successfully');
          alert('Mercado finalizado correctamente');
        } else {
          console.error('Error finishing market');
          alert('Error al finalizar el mercado');
        }
      })
      .catch(error => {
        console.error('Error:', error);
        alert('Error de conexión');
      })
      .finally(() => {
        this.isLoading = false;
      });
  }
}
