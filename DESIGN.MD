---
name: ngopiYuk
colors:
  surface: '#fcf9f8'
  surface-dim: '#dcd9d9'
  surface-bright: '#fcf9f8'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f6f3f2'
  surface-container: '#f0eded'
  surface-container-high: '#eae7e7'
  surface-container-highest: '#e4e2e1'
  on-surface: '#1b1c1c'
  on-surface-variant: '#504441'
  inverse-surface: '#303030'
  inverse-on-surface: '#f3f0f0'
  outline: '#827470'
  outline-variant: '#d4c3be'
  surface-tint: '#77574d'
  primary: '#442a22'
  on-primary: '#ffffff'
  primary-container: '#5d4037'
  on-primary-container: '#d4ada1'
  inverse-primary: '#e7bdb1'
  secondary: '#5e604d'
  on-secondary: '#ffffff'
  secondary-container: '#e1e1c9'
  on-secondary-container: '#636451'
  tertiary: '#003a11'
  on-tertiary: '#ffffff'
  tertiary-container: '#08531e'
  on-tertiary-container: '#80c683'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffdbd0'
  primary-fixed-dim: '#e7bdb1'
  on-primary-fixed: '#2c160e'
  on-primary-fixed-variant: '#5d4037'
  secondary-fixed: '#e4e4cc'
  secondary-fixed-dim: '#c8c8b0'
  on-secondary-fixed: '#1b1d0e'
  on-secondary-fixed-variant: '#474836'
  tertiary-fixed: '#abf4ac'
  tertiary-fixed-dim: '#90d792'
  on-tertiary-fixed: '#002107'
  on-tertiary-fixed-variant: '#07521d'
  background: '#fcf9f8'
  on-background: '#1b1c1c'
  surface-variant: '#e4e2e1'
typography:
  headline-xl:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '700'
    lineHeight: 32px
    letterSpacing: -0.01em
  headline-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 20px
    fontWeight: '700'
    lineHeight: 28px
  title-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 24px
  body-lg:
    fontFamily: Be Vietnam Pro
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Be Vietnam Pro
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: Be Vietnam Pro
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.05em
  label-sm:
    fontFamily: Be Vietnam Pro
    fontSize: 11px
    fontWeight: '500'
    lineHeight: 14px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 4px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  container-padding: 20px
  gutter: 12px
---

## Brand & Style
The design system is built to evoke the sensory experience of a boutique coffee house: warmth, aroma, and community. The target audience is urban explorers and coffee enthusiasts who value atmosphere as much as the brew itself. 

The aesthetic follows a **Modern-Organic** approach, blending high-end editorial layouts with soft, tactile elements. It utilizes generous whitespace to prevent "information overload" common in discovery apps, ensuring the user feels a sense of calm (Asik) while browsing. The style leverages soft gradients and subtle depth to make the UI feel approachable yet premium.

## Colors
The palette is grounded in the "Bean to Cup" journey. 
- **Primary (Espresso):** A deep, rich brown used for primary actions, branding, and high-level headings.
- **Secondary (Crema):** A warm beige used for surfaces, card backgrounds, and subtle containers to provide warmth without the starkness of pure white.
- **Accent (Matcha):** A soft, desaturated green used exclusively for positive status indicators (Open Now), sustainability badges, and success states.
- **Neutral (Charcoal):** Used for body text and icons to ensure high legibility against the creamy backgrounds.

## Typography
The typography system uses **Plus Jakarta Sans** for headlines to provide a modern, friendly, and slightly geometric personality. Its soft curves mirror the "rounded" theme of the UI. 

For functional text and long-form content, **Be Vietnam Pro** is used. It offers exceptional readability at small sizes while maintaining a contemporary, warm tone that complements the brand's approachable nature. All labels use a slightly tighter tracking for a professional, systematic look.

## Layout & Spacing
This design system utilizes a **Mobile-First Fluid Grid**. 
- **Margin:** A standard 20px margin is applied to the left and right of the screen to ensure content doesn't feel cramped.
- **Gutter:** 12px spacing between elements in a row (e.g., two-column menu items).
- **Rhythm:** An 8px base unit drives all spatial relationships. Vertical spacing between sections should typically be `lg` (24px) to allow the "cozy" aesthetic to breathe.
- **Safe Areas:** Bottom navigation bars must account for mobile home indicators with a minimum of 34px bottom padding.

## Elevation & Depth
To maintain a modern and cozy feel, this design system avoids heavy, dark shadows. Instead, it uses:
- **Tonal Elevation:** Higher-level surfaces (like active cards) use the Secondary color (`#F5F5DC`) to differentiate from the Background (`#FAF9F6`).
- **Soft Ambient Shadows:** Elements that require physical presence use a very soft, diffused shadow: `y: 4, blur: 20, color: rgba(93, 64, 55, 0.08)`. The brown tint in the shadow prevents it from looking "muddy" or "grey."
- **Focus States:** Active inputs or cards use a subtle 1px border in the Primary color rather than a glow effect.

## Shapes
The shape language is defined by "Soft Fluidity." 
- **Standard Radius:** 0.5rem (8px) for buttons and small input fields.
- **Large Radius (rounded-lg):** 1rem (16px) for shop cards and bottom sheets to evoke a friendly, welcoming container.
- **Pill Shapes:** Used exclusively for tags (e.g., "Eco-friendly," "Wi-Fi") and the primary "Order" buttons to distinguish them from structural elements.

## Components
- **Coffee Shop Cards:** Feature a full-bleed image at the top with 16px corner radius. Text info is nested in a Crema-colored container with a Matcha-colored "Open" badge in the top-right corner.
- **Menu Lists:** Use a clean, horizontal layout with a small 64px rounded image on the left, title and price in the middle, and a simple Primary-colored "+" icon on the right.
- **Star Ratings:** Use a warm gold-yellow icon rather than the Primary brown to ensure they pop. Empty stars are rendered in a light beige stroke.
- **Bottom Navigation:** A frosted-glass effect (Backdrop Blur) using the Secondary color at 90% opacity. Icons are thick-stroke line icons that fill in with the Primary color when active.
- **Buttons:** Primary buttons are solid Espresso with white text. Secondary buttons are Crema with Espresso text and a subtle 1px border.
- **Input Fields:** Search bars should have a pill-shaped radius (24px+) to appear less like a "form" and more like a friendly tool.