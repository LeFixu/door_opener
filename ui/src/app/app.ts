import { isPlatformBrowser } from '@angular/common';
import { Component, inject, PLATFORM_ID } from '@angular/core';
import { AuthService } from './core/auth/auth.service';
import { I18nService } from './core/i18n/i18n.service';
import { TranslatePipe } from './core/i18n/translate.pipe';
import { DoorControlComponent } from './features/door-control/door-control.component';

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
