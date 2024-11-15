import { Component , OnInit} from '@angular/core';
import { BackgroundThemingService } from '../utils/theming/background-theming.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit{  


  constructor(private themingSrv: BackgroundThemingService) {
  }

  ngOnInit() {
    this.themingSrv.setDashboardBackground();
  }

}
