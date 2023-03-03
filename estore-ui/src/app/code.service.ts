import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap, Observable, of } from 'rxjs';


import { Code } from './code';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class CodeService {

  private codesUrl = 'http://localhost:8080/codes'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }


  //TODO: the rest smile

  addCode(code: Code): Observable<Code> {
    return this.http.post<Code>(this.codesUrl, code, this.httpOptions).pipe(
      tap((newCode: Code) => this.log(`added code: ${newCode.code}`)),
      catchError(this.handleError<Code>('addCode'))
    );
  }

  deleteCode(code: string) {
    const url = `${this.codesUrl}/${code}`;

    return this.http.delete<Code>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted code ${code}`)),
      catchError(this.handleError<Code>('deleteItem'))
    );

  }

  getCodes(): Observable<Code[]> {
    return this.http.get<Code[]>(this.codesUrl)
      .pipe(
        tap(_ => this.log('fetched codes')),
        catchError(this.handleError<Code[]>('getCodes', []))
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
   private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a ItemService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`CodeService: ${message}`);
  }
}
