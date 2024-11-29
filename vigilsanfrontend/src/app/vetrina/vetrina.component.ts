/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Client, Navigation } from '../Client';
import { NavigationExtras, Router } from '@angular/router';
import { MenuItem } from '../header/header.component';

@Component({
  selector: 'app-vetrina',
  templateUrl: './vetrina.component.html',
  styleUrls: ['./vetrina.component.css']
})
export class VetrinaComponent implements OnInit, AfterViewInit {

  menuItems: MenuItem[] = [];

  constructor(public client: Client, private router: Router) {
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.client.textRoute = 'Pagina principale';
    }, 0);
  }

  ngOnInit(): void {
    this.menuItems = this.client.menuItems.filter((e) => e.label !== 'Home');
  }

  scopri() {
    this.router.navigate([Navigation.MAIN_PAGE], { skipLocationChange: true });
  }

  navigate(itemHref: string): void {
    this.client.selectedTab = itemHref;
    if (itemHref === 'main-page/pratiche-vigilanza/scadenziario') {
      this.client.azzeraFiltriRicercaPraticheVigilanza();
    }
    const navigationExtras: NavigationExtras = { skipLocationChange: true };
    this.router.navigate([itemHref], navigationExtras);
    this.client.opened = true;
  }
}
