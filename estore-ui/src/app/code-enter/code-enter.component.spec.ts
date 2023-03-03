import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeEnterComponent } from './code-enter.component';

describe('CodeEnterComponent', () => {
  let component: CodeEnterComponent;
  let fixture: ComponentFixture<CodeEnterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CodeEnterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CodeEnterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
