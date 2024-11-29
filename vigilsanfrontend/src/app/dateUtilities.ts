/*
 * Copyright Regione Piemonte - 2024
 * SPDX-License-Identifier: EUPL-1.2
 */

export class DateUtilities {
  // From datePicker bootstrap to 'yyyy-mm-dd'
  static getDateFromDatePicker(date: { day: number, month: number, year: number }): Date {
    return new Date(`${date.year}-${date.month}-${date.day}`);
  }
  // From datePicker bootstrap to milliseconds
  static getDateMsFromDatePicker(date: { day: number, month: number, year: number }): number {
    return new Date(`${date.year}-${date.month}-${date.day}`).getTime();
  }

  // From 'yyyy-mm-dd' or 'yyyy/mm/dd' to MS date
  static getDateMsFromStringTypeDate(date: string): number | null {
    const parts = date.replace(/[\/\-]/g, '-').split("-").map((part: string) => parseInt(part));
    if (parts && parts.length === 3) {
      return new Date(parts[0], parts[1] - 1, parts[2]).getTime();
    } else {
      return null;
    }
  }
  // From 'yyyy-mm-dd' or 'yyyy/mm/dd' to Date
  static getDateFromStringTypeDate(date: string): Date | null {
    const parts = date.replace(/[\/\-]/g, '-').split("-").map((part: string) => parseInt(part));
    if (parts && parts.length === 3) {
      return new Date(parts[0], parts[1] - 1, parts[2]);
    } else {
      return null;
    }
  }

  // From 'yyyy-mm-dd HH:MM' or 'yyyy/mm/dd HH:MM' to MS date
  static getDateMsFromStringTypeDateTime(date: string): number | null {
    const partsSwap = date.split(' ');
    if (partsSwap && partsSwap.length === 2) {
      const normalizedDateStr = partsSwap[0].replace(/[\/\-]/g, '-');
      const partsDate = normalizedDateStr.split("-").map((part: string) => parseInt(part));
      if (partsDate && partsDate.length === 3) {
        const partTime = partsSwap[1].split(":").map((part: string) => parseInt(part.trim()));
        if (partTime && partTime.length === 2) {
          return new Date(partsDate[0], partsDate[1] - 1, partsDate[2], partTime[0], partTime[1]).getTime();
        } else {
          return null;
        }
      } else {
        return null;
      }
    } else {
      return null;
    }
  }
  // From 'yyyy-mm-dd HH:MM' or 'yyyy/mm/dd HH:MM' to MS date
  static getDateFromStringTypeDateTime(date: string): Date | null {
    const partsSwap = date.split(' ');
    if (partsSwap && partsSwap.length === 2) {
      const normalizedDateStr = partsSwap[0].replace(/[\/\-]/g, '-');
      const partsDate = normalizedDateStr.split("-").map((part: string) => parseInt(part));
      if (partsDate && partsDate.length === 3) {
        const partTime = partsSwap[1].split(":").map((part: string) => parseInt(part.trim()));
        if (partTime && partTime.length === 2) {
          return new Date(partsDate[0], partsDate[1] - 1, partsDate[2], partTime[0], partTime[1]);
        } else {
          return null;
        }
      } else {
        return null;
      }
    } else {
      return null;
    }
  }


  // From date: { day: number, month: number, year: number } and timeString "hh:mm"
  static getDateTimeFromStringTypeDate(date: { day: number, month: number, year: number }, time: string): Date | null {
    const partsSwap = time.split(':').map((part: string) => parseInt(part));
    if (partsSwap && partsSwap.length === 2) {
      return new Date(date.year, date.month - 1, date.day, partsSwap[0], partsSwap[1]);
    } else {
      return null;
    }
  }
  // getDateFromDatePicker(date: { day: number, month: number, year: number }, hour?: number, minute?: number): Date {
  //   if (hour && minute) {
  //     return new Date(date.year, date.month - 1, date.day, hour, minute);
  //   } else {
  //     return new Date(date.year, date.month - 1, date.day);
  //   }
  // }
}
