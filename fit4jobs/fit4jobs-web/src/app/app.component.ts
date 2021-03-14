import { Component } from '@angular/core';
import {RestService} from './core/services/rest.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'fit4jobs-web project';
  constructor(private restService: RestService) {
    this.restService.testContent().subscribe(
    data => {
      this.title = data.message;
    },
    error => {
      alert(error);
    });
  }
}
