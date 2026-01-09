import { Component } from '@angular/core';
import { AllAsks} from '../../Feature/all-asks/all-asks';
import { AllBids} from '../../Feature/all-bids/all-bids';
import { FinishMarketButton} from '../../Feature/finish-market-button/finish-market-button';
import { StartMarketButton} from '../../Feature/start-market-button/start-market-button';

@Component({
  selector: 'app-side-bar',
  imports: [AllAsks, AllBids, FinishMarketButton, StartMarketButton],
  templateUrl: './side-bar.html',
  styleUrl: './side-bar.scss',
})
export class SideBar {
  isDropdownOpen = false;

  toggleDropdown(event: Event): void {
    event.preventDefault();
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  selectOption(option: string): void {
    console.log('Selected:', option);
    // Aquí puedes agregar lógica para mostrar ASKS o BIDS
    // Por ejemplo, emitir un evento o cambiar una vista
  }
}
