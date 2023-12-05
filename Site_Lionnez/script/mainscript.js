const modalVisus = document.querySelectorAll('.aboutcard_modal'),
    modalButtons = document.querySelectorAll('.button_aboutcard'),
    modalCloses = document.querySelectorAll('.fecha_modal')

let modal = function (modalClick) {
    modalVisus[modalClick].classList.add('active-modal')
}

modalButtons.forEach((modalButtons, i) => {
    modalButtons.addEventListener('click', () => {
        modal(i)
    })
})

modalCloses.forEach((modalClose) => {
    modalClose.addEventListener('click', () => {
        modalVisus.forEach((modalVisu) => {
            modalVisu.classList.remove('active-modal')
        })
    })
})

/* MENU */

const navMenu = document.getElementById('nav__menu'),
    navMenuBurguer = document.getElementById('id_nav_menuburguer'),
    navClose = document.getElementById('id_nav_close')

/* abre */
if (navMenuBurguer) {
    navMenuBurguer.addEventListener('click', () => {
        navMenu.classList.add('show-menu')
    })
}

/* fecha */
if (navClose) {
    navClose.addEventListener('click', () => {
        navMenu.classList.remove('show-menu')
    })
}

/* fechando o menu a cada item */
const navLink = document.querySelectorAll('.nav_li_link')

function linkAction() {
    const navMenu = document.getElementById('nav__menu')
    navMenu.classList.remove('show-menu')
}
navLink.forEach(n => n.addEventListener('click', linkAction))

/*SLIDE SHOW CLIENTE */
let clienteSwiper = new Swiper(".clientes_container", {
    spaceBetween: 24,
    loop: true,
    grabCursor: true,

    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },

    breakpoints: {
        967: {
            spaceBetween: 80,
            slidesPerView: 2,
        },

        767: {
            slidesPerView: 2,
            spaceBetween: 80,
        },
        320: {
            slidesPerView: 1,
            spaceBetween: 80,
        },
    },

})


/*SLIDE SHOW HOME */
let homeSwiper = new Swiper(".home-swiper", {
    spaceBetween: 30,
    loop: 'true',

    pagination: {
        el: ".swiper-pagination",
        clickable: true
    },
});


/*SLIDE SHOW EQUIPE */

let equipeSwiper = new Swiper(".equipe-swiper", {
    spaceBetween: 24,
    loop: 'true',
    slidesPerView: 'auto',
    centeredSlides: true,
    grabCursor: true,

    pagination: {
        el: ".swiper-pagination",
        dynamicBullets: true
    },

    breakpoints: {
        992: {
            spaceBetween: 80,
        },

        767: {
            spaceBetween: 80,
        },

        320: {
            spaceBetween: 80,
        }
    },

});


/* FAQ */

const accordionItems = document.querySelectorAll('.questions_item')

accordionItems.forEach((item) => {
    const accordionHeader = item.querySelector('.questions_header')

    accordionHeader.addEventListener('click', () => {
        const openItem = document.querySelector('.accordion-open')

        toggleItem(item)

        if (openItem && openItem !== item) {
            toggleItem(openItem)
        }
    })
})

const toggleItem = (item) => {
    const accordionContent = item.querySelector('.questions_content')

    if (item.classList.contains('accordion-open')) {
        accordionContent.removeAttribute('style')
        item.classList.remove('accordion-open')
    } else {
        accordionContent.style.height = accordionContent.scrollHeight + 'px'
        item.classList.add('accordion-open')
    }

}

/*MENU*/

const sections = document.querySelectorAll('section[id]')

function scrollActive() {
    const scrollY = window.pageYOffset

    sections.forEach(current => {
        const sectionHeight = current.offsetHeight,
            sectionTop = current.offsetTop - 58,
            sectionId = current.getAttribute('id')

        if (scrollY > sectionTop && scrollY <= sectionTop + sectionHeight) {
            document.querySelector('.nav_menu a[href*=' + sectionId + ']').classList.add('active-link')
        } else {
            document.querySelector('.nav_menu a[href*=' + sectionId + ']').classList.remove('active-link')
        }
    })
}
window.addEventListener('scroll', scrollActive)

/* ROLAR */
function scrollUp() {
    const scrollUp = document.getElementById('scroll-up');
    if (this.scrollY >= 400) scrollUp.classList.add('show-scroll'); else scrollUp.classList.remove('show-scroll')
}
window.addEventListener('scroll', scrollUp)




