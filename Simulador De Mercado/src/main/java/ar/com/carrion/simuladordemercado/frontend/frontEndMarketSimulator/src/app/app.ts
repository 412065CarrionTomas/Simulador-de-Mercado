import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SideBar} from './Core/side-bar/side-bar';
import { MainGraphicComponent } from './Core/main-graphic/main-graphic';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, SideBar, MainGraphicComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('frontEndMarketSimulator');
}

