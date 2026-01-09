import { Component } from '@angular/core';
import { CommonModule} from '@angular/common';

@Component({
  selector: 'app-start-market-button',
  imports: [CommonModule],
  templateUrl: './start-market-button.html',
  styleUrl: './start-market-button.scss',
})
export class StartMarketButton {
  isLoading = false;

  startMarket(): void {
    this.isLoading = true;

    fetch('http://localhost:8080/api/start-market',{
    method: 'POST'})
      .then(response =>{
        if (response.ok){
          console.log ('Market started successfully');
          alert ('Mercado iniciado correctamente');
        }
        else {
          console.error ('Error starting market');
          alert ('Error al iniciar el mercado');
        }
      })
      .catch (error =>{
        console.error ('Error:', error);
        alert ('Error de conexión');
      });
  }


  protected readonly statusbar = statusbar;
}
