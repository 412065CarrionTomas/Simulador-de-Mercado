import { Component } from '@angular/core';

@Component({
  selector: 'app-order',
  imports: [],
  templateUrl: './order.html',
  styleUrl: './order.scss',
})
export class Order {
  price = 0.00;
  typeOrder = "";
  quantity = 0;
  time = "";
}
