import { writable } from 'svelte/store';

export const usernameStore = writable(null);

export const postList = writable([]);
export const searchKeyword = writable('');

export const selectedCategory = writable('title,body,author')
export const selectedSorting = writable('createDesc')
