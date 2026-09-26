import { Component, inject, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { DoorApiService } from './door-api.service';
import { TranslatePipe } from '../../core/i18n/translate.pipe';

@Component({
  selector: 'app-door-control',
  imports: [ReactiveFormsModule, TranslatePipe],
  templateUrl: './door-control.component.html',
  styleUrl: './door-control.component.scss',
})
export class DoorControlComponent {
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly doorApi = inject(DoorApiService);
  protected readonly isSubmitting = signal(false);
  protected readonly feedback = signal<{ type: 'success' | 'error'; message: 'updateSuccess' | 'updateError' } | null>(null);

  protected readonly form = this.formBuilder.group({
    id: ['', [Validators.required, Validators.pattern(
      /^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i,
    )]],
    open: [true],
  });

  protected submit(): void {
    if (this.form.invalid || this.isSubmitting()) {
      this.form.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);
    this.feedback.set(null);
    this.doorApi.updateDoor(this.form.getRawValue())
      .pipe(finalize(() => this.isSubmitting.set(false)))
      .subscribe({
        next: () => this.feedback.set({ type: 'success', message: 'updateSuccess' }),
        error: () => this.feedback.set({ type: 'error', message: 'updateError' }),
      });
  }
}
