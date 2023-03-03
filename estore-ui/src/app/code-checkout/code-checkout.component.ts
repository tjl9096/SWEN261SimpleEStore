import { Component, OnInit } from '@angular/core';
import { MessageService } from '../message.service';
import { CodeService } from '../code.service';
import { Code } from '../code';
import { CheckoutComponent } from '../checkout/checkout.component';
import { subscribeOn } from 'rxjs';

@Component({
  selector: 'app-code-checkout',
  templateUrl: './code-checkout.component.html',
  styleUrls: ['./code-checkout.component.css']
})
export class CodeCheckoutComponent implements OnInit {
  codes: Code[] = []

  constructor(
    private messageService: MessageService,
    private codeService: CodeService,
    private checkoutComponent: CheckoutComponent) { }

  ngOnInit(): void {
    this.getCodes();
  }

  getCodes(): void {
    this.codeService.getCodes()
      .subscribe(codes => this.codes = codes);
  }

  check(c: string): void {
    c = c.trim();
    if (!c) { return; }
    this.messageService.add("checking code: " + c);
    
    for(let i = 0; i < this.codes.length; i++){
      if (c == this.codes[i].code){
        this.checkoutComponent.getTotal(this.codes[i].percent)
        break
      }
    }
  }

}
