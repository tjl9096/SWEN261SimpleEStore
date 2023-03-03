import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Item } from '../item';
import { CartService } from '../cart.service';
import { subscribeOn } from 'rxjs';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  items: Item[] = []
  total: Number = 0

  constructor(
    private location: Location,
    private cartService: CartService,
    ) {}

  ngOnInit(): void {
    this.getItems();
    this.getTotal(0);
  }

  getItems(): void {
    this.cartService.getItems()
    .subscribe(items => this.items = items);
  }

  getTotal(x: number): void {
    this.cartService.getTotal()
      .subscribe(total => this.total = Number(total) * ((100 - x)/100));
    
  }

  goBack(): void {
    this.location.back();
  }
}
