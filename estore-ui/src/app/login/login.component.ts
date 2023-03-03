import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService  } from '../cart.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private router: Router,
    private cartService: CartService
    ) { }

  ngOnInit(): void {
  }

  login(username: string): void {
    username = username.trim();
    if (!username) {
      return;
    }
    if (username == 'admin') {
      this.router.navigateByUrl('/admin/edit');
    } else {
      this.router.navigateByUrl('/user/items');
      this.cartService.changeUser(username).subscribe();
    }
  }

}
