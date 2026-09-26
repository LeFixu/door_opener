import { DOCUMENT } from '@angular/common';
import { afterNextRender, inject, Injectable, signal } from '@angular/core';
import english from './locales/en.json';
import german from './locales/de.json';
import french from './locales/fr.json';

type Locale = 'en' | 'de' | 'fr';

export type TranslationKey = keyof typeof english;

const translations: Record<Locale, Record<TranslationKey, string>> = {
  en: english,
  de: german,
  fr: french,
};

@Injectable({ providedIn: 'root' })
export class I18nService {
  private readonly document = inject(DOCUMENT);
  readonly locale = signal<Locale>('en');

  constructor() {
    this.updateDocumentLanguage('en');
    afterNextRender(() => {
      const browserLanguages = this.document.defaultView?.navigator.languages ?? [];
      const browserLanguage = this.document.defaultView?.navigator.language;
      const locale = [...browserLanguages, browserLanguage]
        .map((language) => language?.toLowerCase().split(/[-_]/)[0])
        .find((language): language is Locale => language === 'de' || language === 'fr' || language === 'en') ?? 'en';
      this.locale.set(locale);
      this.updateDocumentLanguage(locale);
    });
  }

  translate(key: TranslationKey): string {
    return translations[this.locale()][key];
  }

  setLocale(locale: Locale): void {
    this.locale.set(locale);
    this.updateDocumentLanguage(locale);
  }

  private updateDocumentLanguage(locale: Locale): void {
    this.document.documentElement.lang = locale;
    this.document.title = translations[locale].documentTitle;
  }
}