import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Test} from '../models/test';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private http: HttpClient) { }

  testContent(): Observable<Test> {
    return this.http.get<Test>('http://localhost:8080/api/test/all');
  }
}
