import { Component, OnInit } from '@angular/core';
import { Code } from '../code';
import { CodeService } from '../code.service';

@Component({
  selector: 'app-code-enter',
  templateUrl: './code-enter.component.html',
  styleUrls: ['./code-enter.component.css']
})
export class CodeEnterComponent implements OnInit {
  codes: Code[] = [];

  constructor(private codeService: CodeService) { }

  ngOnInit(): void {
    this.getCodes();
  }

  enter(code: string, percent: number): void {
      code = code.trim();
      if (!code || !percent ) { return; }
      this.codeService.addCode({ code, percent} as Code)
        .subscribe(newCode => {
          this.codes.push(newCode);
        });
    }

  delete(code: Code): void {
    this.codes = this.codes.filter(c => c !== code);
    this.codeService.deleteCode(code.code).subscribe();
  }


  getCodes(): void {
    this.codeService.getCodes()
      .subscribe(codes => this.codes = codes);
  }

}

