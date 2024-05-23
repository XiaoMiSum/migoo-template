declare module 'vue' {
  export interface GlobalComponents {
    Icon: (typeof import('@/components/Icon'))['Icon']
    OnesTag: (typeof import('@/components/OnesTag'))['OnesTag']
  }
}

export {}
