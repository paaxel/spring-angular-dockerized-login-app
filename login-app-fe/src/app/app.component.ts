import { Component } from '@angular/core';
import { SetLanguageService } from './utils/language/set-language.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(private setLanguageService: SetLanguageService){
  }

  ngOnInit(){
    this.setLanguageService.inizializeLanguage()
  }
}
