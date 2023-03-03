import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ItemDetailComponent } from './item-detail/item-detail.component';
import { ItemsComponent } from './items/items.component';
import { ItemSearchComponent } from './item-search/item-search.component';
import { MessagesComponent } from './messages/messages.component';
import { LoginComponent } from './login/login.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { CodeEnterComponent } from './code-enter/code-enter.component';
import { CodeCheckoutComponent } from './code-checkout/code-checkout.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    ItemsComponent,
    ItemDetailComponent,
    MessagesComponent,
    ItemSearchComponent,
    LoginComponent,
    UserDetailComponent,
    CartComponent,
    CheckoutComponent,
    CodeEnterComponent,
    CodeCheckoutComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }