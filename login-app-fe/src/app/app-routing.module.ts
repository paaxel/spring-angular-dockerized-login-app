import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';


@NgModule({
  imports: [RouterModule.forRoot([
              {path: "**", redirectTo: "/login"},

              {
                path: "dashboard",
                loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule)
              }


          ], { useHash: true , onSameUrlNavigation: "reload"})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
