import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Item } from '../item';
import { CartService } from '../cart.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  items: Item[] = [];

  constructor(
    private location: Location,
    private cartService: CartService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getItems();
  }

  goBack(): void {
    this.location.back();
  }

  getItems(): void {
    this.cartService.getItems()
    .subscribe(items => this.items = items);
  }

  delete(item: Item): void {
    this.items = this.items.filter(i => i !== item);
    this.cartService.deleteItem(item.id).subscribe();
  }

  checkout(): void {
    this.router.navigateByUrl('user/checkout')
  }

}
