import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeCheckoutComponent } from './code-checkout.component';

describe('CodeCheckoutComponent', () => {
  let component: CodeCheckoutComponent;
  let fixture: ComponentFixture<CodeCheckoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CodeCheckoutComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CodeCheckoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
