import { Component } from '@angular/core';
import { Route, Router, RouterOutlet } from '@angular/router';
import { SharedModule } from './modules/shared/shared.module';
import { SignupComponent } from './modules/auth/signup/signup.component';
import { UserStorageService } from './modules/auth/services/user-storage.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SharedModule, SignupComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'quizWeb';

  isUserLoggedIn: boolean = UserStorageService.isUserLoggedIn();

  isAdminLoggedIn: boolean = UserStorageService.isAdminLoggedIn();


  constructor(private router: Router){}

  ngOnInIt() {
    this.router.events.subscribe(event=>{
      this.isUserLoggedIn = UserStorageService.isUserLoggedIn();
      this.isAdminLoggedIn = UserStorageService.isAdminLoggedIn();
    })
  }

  logout(){
    UserStorageService.signOut();
    this.router.navigateByUrl('login');
  }
}
