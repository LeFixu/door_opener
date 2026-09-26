import { isPlatformBrowser } from '@angular/common';
import { Component, inject, PLATFORM_ID } from '@angular/core';
import { DoorControlComponent } from './door-control.component';
import { AuthService } from './auth.service';
import { I18nService } from './i18n.service';
import { TranslatePipe } from './translate.pipe';

@Component({
  imports: [DoorControlComponent, TranslatePipe],
  selector: 'app-root',
  styleUrl: './app.scss',
  templateUrl: './app.html',
})
export class App {
  protected readonly auth = inject(AuthService);
  protected readonly i18n = inject(I18nService);
  private readonly platformId = inject(PLATFORM_ID);

  protected changeLocale(event: Event): void {
    const locale = (event.target as HTMLSelectElement).value;
    if (locale === 'en' || locale === 'de' || locale === 'fr') {
      this.i18n.setLocale(locale);
    }
  }

  constructor() {
    if (isPlatformBrowser(this.platformId)) {
      this.auth.loadUser();
    }
  }
}
