import { Component } from '@angular/core';
import { Order} from '../order/order';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-all-bids',
    imports: [CommonModule],
  templateUrl: './all-bids.html',
  styleUrl: './all-bids.scss',
  standalone:true
})
export class AllBids {
  orders: Order[] = [];

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders(): void {
    fetch('http://localhost:8080/api/getAllBids')
      .then(response => response.json())
      .then(data => {
        this.orders = data.map((order: any) => ({
          price: order.price,
          quantity: order.quantity
        }));
      })
      .catch(error => {
        console.error('Error fetching orders:', error);
      });
  }
}
