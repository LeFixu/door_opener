import { Component, inject, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { finalize } from 'rxjs';
import { DoorApiService } from './door-api.service';

@Component({
  selector: 'app-door-control',
  imports: [ReactiveFormsModule],
  templateUrl: './door-control.component.html',
  styleUrl: './door-control.component.scss',
})
export class DoorControlComponent {
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly doorApi = inject(DoorApiService);

  protected readonly isSubmitting = signal(false);
  protected readonly feedback = signal<{ type: 'success' | 'error'; message: string } | null>(null);

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
        next: () => this.feedback.set({ type: 'success', message: 'Door state updated.' }),
        error: () => this.feedback.set({ type: 'error', message: 'The door could not be updated. Check the connection and try again.' }),
      });
  }
}
