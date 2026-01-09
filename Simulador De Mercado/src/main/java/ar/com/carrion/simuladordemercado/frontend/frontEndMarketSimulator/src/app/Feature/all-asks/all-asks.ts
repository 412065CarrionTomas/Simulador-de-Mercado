import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Order} from '../order/order';

@Component({
  selector: 'app-all-asks',
  imports: [CommonModule],
  templateUrl: './all-asks.html',
  styleUrl: './all-asks.scss',
  standalone:true
})
export class AllAsks implements OnInit{
  orders: Order[] = [];

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders(): void {
    fetch('http://localhost:8080/api/getAllAsks')
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
