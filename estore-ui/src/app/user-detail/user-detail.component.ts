import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Item } from '../item';
import { ItemService } from '../item.service';
import { CartService  } from '../cart.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  item: Item | undefined;

  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private location: Location,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.getItem();
  }

  getItem(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.itemService.getItem(id)
      .subscribe(item => this.item = item);
  }

  goBack(): void {
    this.location.back();
  }

  addCart(): void {
    this.cartService.addItem(this.item as Item).subscribe();
  }
}
